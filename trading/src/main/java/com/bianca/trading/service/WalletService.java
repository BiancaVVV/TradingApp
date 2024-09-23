package com.bianca.trading.service;

import com.bianca.trading.modal.Order;
import com.bianca.trading.modal.User;
import com.bianca.trading.modal.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet,Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
