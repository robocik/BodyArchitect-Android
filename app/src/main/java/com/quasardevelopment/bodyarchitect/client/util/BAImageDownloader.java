package com.quasardevelopment.bodyarchitect.client.util;

import android.content.Context;
import android.graphics.Bitmap;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.PictureInfoDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 28.05.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class BAImageDownloader extends BaseImageDownloader {
    public BAImageDownloader(Context context) {
        super(context);
    }

    public BAImageDownloader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);    //To change body of overridden methods use File | Settings | File Templates.
    }


    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {

        if(ApplicationState.getCurrent()!=null && !ApplicationState.getCurrent().isOffline)
        {
            BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService();
            try {
                PictureInfoDTO info = new PictureInfoDTO();
                info.pictureId= UUID.fromString(imageUri);
                info.hash="";//this is needed to prevent null pointer exception only
                Bitmap bmp=service.GetImage(info);

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0, os);
                return new ByteArrayInputStream(os.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.getStreamFromOtherSource(imageUri,extra);
    }
}
