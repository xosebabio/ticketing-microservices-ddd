package org.xbs.shared.application;

import org.springframework.transaction.annotation.Transactional;

public interface QueryHandler<Q extends Query, R> {
    @Transactional
    R handle(Q query);
}
