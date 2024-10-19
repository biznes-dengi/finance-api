package com.maksyank.finance.saving.mapper.context;

import com.maksyank.finance.saving.domain.Saving;

public class SavingContext {

    public Saving saving;

    public SavingContext(Saving saving) {
        this.saving = saving;
    }
}
