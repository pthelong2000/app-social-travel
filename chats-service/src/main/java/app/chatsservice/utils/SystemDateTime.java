package app.chatsservice.utils;

import java.time.LocalDateTime;

public class SystemDateTime {

    private LocalDateTime systemDateTime;

    public SystemDateTime() {
        this.systemDateTime = LocalDateTime.now();
    }

    public LocalDateTime now() {
        return systemDateTime;
    }
}
