package com.septems.avinash.ngrid.OrderForm.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avinash on 2/12/2018.
 */

public class Order {
    public String Rice;
    public String ToorDal;
    public String Wheat;
    public String Rajama;
    public String Choole;
    public String Matar;

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Order(String Rice,  String ToorDal, String Wheat, String Rajama , String Choole, String Matar ) {
        this.Rice = Rice;
        this.ToorDal = ToorDal;
        this.Wheat = Wheat;
        this.Rajama = Rajama;
        this.Choole = Choole;
        this.Matar = Matar;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Rice", Rice);
        result.put("ToorDal", ToorDal);
        result.put("Wheat", Wheat);
        result.put("Rajama", Rajama);
        result.put("Choole", Choole);
        result.put("Matar", Matar);

        return result;
    }
    // [END post_to_map]
}
