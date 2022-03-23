import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';
import HomePage from './home';

const Home = () =>
{
  return (
    <GoogleReCaptchaProvider reCaptchaKey='6LcxtwQfAAAAAM3jf_vTeGpDPWhU_lbSy-FDwxCP'>
      <HomePage/>
    </GoogleReCaptchaProvider>
  );
}

export default Home;