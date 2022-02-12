package com.company.enesdeniz.Factory;

import com.company.enesdeniz.Interface.RealEstateInterface;
import com.company.enesdeniz.model.RealEstate;

public class RealEstateFactory implements RealEstateInterface {

    @Override
    public RealEstate createRealEstate(String province, String district, String numberOfRooms) {

        RealEstate realEstate = new RealEstate(province, district, numberOfRooms);

        return realEstate;
    }

}
