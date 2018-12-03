package com.rbkmoney.java.damsel.constant;

import java.util.Map;

public class MpiState {

    public static final String MPI_STATE = "mpi_state";
    public static final String ENABLE = "true";
    public static final String DISABLE = "false";

    public static boolean isMpiEnabled(Map<String, String> options) {
        String mpiState = options.getOrDefault(MpiState.MPI_STATE, MpiState.ENABLE);
        return MpiState.ENABLE.equalsIgnoreCase(mpiState);
    }
}