package com.instanect.accountcommon;

import java.util.HashMap;

/**
 * Created by AKS on 10/4/2017.
 */

public interface AuthorizationHeaderInterface {

    HashMap<String, String> getAuthorizationHeaderMapWithPassword();

    HashMap<String, String> getAuthorizationHeaderMapWithToken();

    HashMap<String, String> getDummyHeader();


}
