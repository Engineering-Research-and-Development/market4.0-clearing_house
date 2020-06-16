package it.eng.idsa.clearinghouse.api.service;

import it.eng.idsa.clearinghouse.api.config.HLFConfigProps;
import it.eng.idsa.clearinghouse.client.ClearingHouseService;
import it.eng.idsa.clearinghouse.client.impl.ClearingHouseServiceImpl;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Log
@Service
@DependsOn("HLFConfigProps")
public class ClearingHouseServiceManager {

    @Autowired
    private HLFConfigProps hlfConfigProps;
    private ClearingHouseService clearingHouseService;

    public ClearingHouseServiceImpl build() throws Exception {
        return new ClearingHouseServiceImpl(hlfConfigProps);
    }

    public ClearingHouseService getClearingHouseService() throws Exception {
        if (null == clearingHouseService) {
            clearingHouseService = build();
        }
        return clearingHouseService;
    }
}
