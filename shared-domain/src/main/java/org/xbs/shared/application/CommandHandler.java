package org.xbs.shared.application;

import org.springframework.transaction.annotation.Transactional;

public interface CommandHandler<C extends Command> {
    @Transactional
    void handle(C command);
}
