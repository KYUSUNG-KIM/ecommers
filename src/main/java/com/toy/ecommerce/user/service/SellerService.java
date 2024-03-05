package com.toy.ecommerce.user.service;

import com.toy.ecommerce.user.entity.Seller;
import com.toy.ecommerce.user.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;


    @Transactional(readOnly = true)
    public Optional<Seller> getVerifiedSellerByEmail(String email) {

        return sellerRepository.findOneByEmailAndVerifyIsTrue(email);
    }
}
