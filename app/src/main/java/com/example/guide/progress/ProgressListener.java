package com.example.guide.progress;

public interface ProgressListener {

    void progress(long bytesRead, long contentLength, boolean done);

}
