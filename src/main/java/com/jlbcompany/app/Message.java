package com.jlbcompany.app;

import java.time.LocalDateTime;

public record Message(String message,
                      LocalDateTime created) {
}
