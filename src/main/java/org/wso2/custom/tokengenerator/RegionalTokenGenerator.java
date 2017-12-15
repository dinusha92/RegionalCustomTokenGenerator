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
 *
 * This class contains the extension point to append region for the access token.
 * Region should be taken form a system variable.
 *
 */
public class RegionalTokenGenerator extends OauthTokenIssuerImpl {

    private static final Log log = LogFactory.getLog(RegionalTokenGenerator.class);
    private static final String REGION = "region";
    private static final String DELIMITER = "-";

    @Override
    public
    String accessToken(OAuthTokenReqMessageContext tokReqMsgCtx) throws OAuthSystemException {
        //generate the accesstoken
        return issueAccessToken(super.accessToken(tokReqMsgCtx));
    }

    @Override
    public String accessToken(OAuthAuthzReqMessageContext oauthAuthzMsgCtx) throws OAuthSystemException {
        //generate the accesstoken
        return issueAccessToken(super.accessToken(oauthAuthzMsgCtx));
    }

    @Override
    public String refreshToken(OAuthTokenReqMessageContext tokReqMsgCtx) throws OAuthSystemException {
        //generate refresh token
        return issueAccessToken(super.refreshToken(tokReqMsgCtx));
    }

    @Override
    public String refreshToken(OAuthAuthzReqMessageContext oauthAuthzMsgCtx) throws OAuthSystemException {
        //generate refresh token
        return issueAccessToken(super.refreshToken(oauthAuthzMsgCtx));
    }

    /**
     * This method will get the access token as input and append the region as a prefix if it is available.
     *
     * @param accessToken generated default access token calling super method
     * @return String accessToken as a string
     */
    private String issueAccessToken(String accessToken) {
        //Reading the region value from the property "region"
        String region = System.getProperty(REGION);
        //check if region is null or not and append region- to access token
        if (region != null) {
            log.debug("Region: " + region);
            accessToken = region + DELIMITER + accessToken;
        }
        return accessToken;
    }
}
