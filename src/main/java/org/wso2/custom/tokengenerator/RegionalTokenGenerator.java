/*
* Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.custom.tokengenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.OauthTokenIssuerImpl;

/**
 * This class contains the extension point to append region for the access token
 * Region should be taken form a system variable
 */
public class RegionalTokenGenerator extends OauthTokenIssuerImpl {

    private static final Log log = LogFactory.getLog(RegionalTokenGenerator.class);
    private static final String REGION = "region";

    @Override
    public String accessToken(OAuthTokenReqMessageContext tokReqMsgCtx) throws OAuthSystemException {

        //Reading the region value from the property "region"
        String region = System.getProperty(REGION);
        //generate the accesstoken
        String accessToken = super.accessToken(tokReqMsgCtx);
        //check if region is null or not and append region- to access token
        if (region != null) {
            accessToken = region + "-" + accessToken;
        }
        return accessToken;
    }

    @Override
    public String accessToken(OAuthAuthzReqMessageContext oauthAuthzMsgCtx) throws OAuthSystemException {
        //Reading the region value from the property "region"
        String region = System.getProperty(REGION);
        //generate the accesstoken
        String accessToken = super.accessToken(oauthAuthzMsgCtx);
        //check if region is null or not and append region- to access token
        if (region != null) {
            accessToken = region + "-" + accessToken;
        }
        return accessToken;
    }
}
