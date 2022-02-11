package app.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Data
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "uploaded";

}
