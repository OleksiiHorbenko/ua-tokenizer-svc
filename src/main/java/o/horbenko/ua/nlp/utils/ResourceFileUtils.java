package o.horbenko.ua.nlp.utils;

import lombok.experimental.UtilityClass;

import java.io.InputStream;

@UtilityClass
public class ResourceFileUtils {

    public InputStream asInputStream(String relativePathInResourcesDirectory) {
        return ResourceFileUtils.class
                .getClassLoader()
                .getResourceAsStream(relativePathInResourcesDirectory);
    }

}
