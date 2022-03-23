import "cropperjs/dist/cropper.min.css";
import * as React from 'react';
import Cropper from "cropperjs";
import { useGoogleReCaptcha } from "react-google-recaptcha-v3";
import { Button, Modal, Alert,
    Row, Col } from 'antd';

import http from '../../http_common';

const HomePage : React.FC = () => {
    const { executeRecaptcha } = useGoogleReCaptcha();
    const [error, setError] = React.useState<string>("");
    const [isModalVisible, setIsModalVisible] = React.useState(false);
    const imgRef = React.useRef<HTMLImageElement>(null);
    const prevRef = React.useRef<HTMLImageElement>(null);
    const [cropperObj, setCropperObj] = React.useState<Cropper>();
    const [imageView, setImageView] = React.useState<string>("https://www.securityindustry.org/wp-content/uploads/sites/3/2018/05/noimage.png");


    // React.useEffect(()=>{
        
    // },[]);

    //вибір файла
    const handleChangeFile = async (e: any) => {
      const file = (e.target.files as FileList)[0];
      if (file) {
        const url = URL.createObjectURL(file);

        await setIsModalVisible(true);
        console.log("Image ref ", imgRef);
        let cropper = cropperObj;
        if (!cropper) {
          cropper = new Cropper(imgRef.current as HTMLImageElement, {
            aspectRatio: 1 / 1,
            viewMode: 1,
            preview: prevRef.current as HTMLImageElement,
          });
        }
        cropper.replace(url);
        e.target.value="";
        setCropperObj(cropper);
      }
    };
  
    const handleOk = () => {
        const base64 = cropperObj?.getCroppedCanvas().toDataURL() as string;
        console.log("Cropper data: ", base64);
        setImageView(base64);
      setIsModalVisible(false);
    };
  
    const handleCancel = () => {
      setIsModalVisible(false);
    };

    const handleLogin = async () => {
      if (!executeRecaptcha) {
        setError("Помилка перевірки Captcha");
        return;
      }
      const token = await executeRecaptcha();
      http.post("api/public/login", {
        username: "semen@gmail.com",
        password: "123456",
        token: token
      });
      console.log("begin login", token);
    };
    return (
      <>
        <h1>Головна сторінка</h1>

        {!!error &&  <Alert message={error} type="error" />}

        <Button type="primary" onClick={handleLogin}>
            Login
        </Button>

        <br/>

        <input
          id="uploadimg"
          className="d-none"
          type="file"
          onChange={handleChangeFile}
        />

        <img src={imageView} alt="" width="250" />



        <Modal
          title="Basic Modal"
          visible={isModalVisible}
          onOk={handleOk}
          onCancel={handleCancel}
        >
          <p>Some contents...</p>
          <Row>
            <Col span={18}>
              <div>
                <img
                  src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Ukrainian_girl_by_Nikolay_Rachkov_%282nd_half_19_c.%2C_Chernigov_museum%29.jpg/435px-Ukrainian_girl_by_Nikolay_Rachkov_%282nd_half_19_c.%2C_Chernigov_museum%29.jpg"
                  width="100%"
                  ref={imgRef}
                />
              </div>
            </Col>
            <Col span={6}>
              <div
                ref={prevRef}
                style={{
                  height: "100px",
                  border: "1px solid silver",
                  overflow: "hidden",
                }}
              >
                {" "}
              </div>
            </Col>
          </Row>

          <p>Some contents...</p>
          <p>Some contents...</p>
        </Modal>
      </>
    );
}
export default HomePage;

