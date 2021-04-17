package com.mohammali.web.bm.common.data;

import java.util.List;
import java.util.Optional;

public interface BaseRepo<T, I> {

    List<T> findAll();

    Optional<T> findById(I id);
}
