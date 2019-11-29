package com.rbkmoney.java.damsel.utils.extractors;

import com.rbkmoney.damsel.withdrawals.provider_adapter.ProcessResult;

public class WithdrawalsProviderAdapterPackageExtractors {

    public static boolean isSleep(ProcessResult processResult) {
        return processResult.getIntent().isSetSleep();
    }

    public static boolean isSuccess(ProcessResult processResult) {
        return processResult.getIntent().getFinish().getStatus().isSetSuccess();
    }

}