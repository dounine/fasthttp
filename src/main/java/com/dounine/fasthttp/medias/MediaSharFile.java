package com.dounine.fasthttp.medias;

import java.io.File;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public class MediaSharFile extends MediaFile implements IMediaSharFile {

    public MediaSharFile(File file){
        this.file = file;
    }

    @Override
    public long sharSize() {
        return 0;
    }
}
