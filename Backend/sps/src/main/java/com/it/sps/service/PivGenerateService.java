package com.it.sps.service;

import com.it.sps.dto.PivChargeResponseDto;
import com.it.sps.dto.PivGenerateDto;
import com.it.sps.dto.PivRequestDTO;
import java.util.Optional;

public interface PivGenerateService {

    Optional<PivGenerateDto> getCustomerDetails(String applicationNo);

    String getPivTypeName(String titleCd);

    PivChargeResponseDto getChargeAccounts(String titleCd, String pivType, String estimateNo);

    String savePiv(PivRequestDTO request) throws Exception;
}