package com.bianca.trading.modal;

import com.bianca.trading.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnaled = false;
    private VerificationType sendTo;
}
