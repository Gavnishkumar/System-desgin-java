package service;

import java.util.List;
import model.DeliveryPartner;
import java.util.ArrayList;
public class DeliveryPartnerService {
    List<DeliveryPartner> deliveryPartners;
    static DeliveryPartnerService instance=null;
    private DeliveryPartnerService() {
        deliveryPartners= new ArrayList<>();
    }  
    public static DeliveryPartnerService getInstance() {
        if(instance == null) {
            instance = new DeliveryPartnerService();
        }
        return instance;
    }

    public void addDeliveryPartner(DeliveryPartner partner) {
        deliveryPartners.add(partner);
    }

    public DeliveryPartner searchDeliveryPartner() {
        return deliveryPartners.get(0);
    }
}
