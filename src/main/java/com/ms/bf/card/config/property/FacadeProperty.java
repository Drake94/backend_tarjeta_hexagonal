package com.ms.bf.card.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "facade")
public class FacadeProperty {
    private String urlBase;
    private String urlCreate;
    private String urlUpdate;
    private String urlInternal;



    //public String getUrl(String url, String complement) {
    //    return urlBase.concat(url).concat(complement);
    ///}
    public String getUpdateUrl(String urlBase , String urlUpdate) {
        return urlBase.concat(urlUpdate);
    }

    public String getUrl(String urlBase , String urlInternal, String complement) {
        return urlBase.concat(urlInternal).concat(complement);

    }
    public String getUrlCreates(String url) {
        return urlBase.concat(url);
    }


}
