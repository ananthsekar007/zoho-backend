package com.zoho.backend.provider;
import com.plaid.client.ApiClient;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import com.zoho.backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class PlaidProvider {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${plaid.clientId}")
    private String clientId;

    @Value("${plaid.secret}")
    private String secret;

    private PlaidApi plaidApi;

    public PlaidApi getPlaidClient () {
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", clientId);
        apiKeys.put("secret", secret);
        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Sandbox); // or equivalent, depending on which environment you're calling into
        return apiClient.createService(PlaidApi.class);
    }

    public String createLinkToken(String userId) throws IOException {
        PlaidApi plaidClient = getPlaidClient();

        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(userId);

        LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                .user(user)
                .clientName("GenBooks")
                .products(Arrays.asList(Products.TRANSACTIONS))
                .countryCodes(Arrays.asList(CountryCode.US))
                .language("en")
                .webhook("https://webhook.site/f9457aa4-13c1-46c5-8b81-e80f7d32b81b");

        Response<LinkTokenCreateResponse> response = plaidClient.linkTokenCreate(request).execute();
            String linkToken = response.body().getLinkToken();
            logger.info("The response if not null " + linkToken);
            return linkToken;
    }

    public AccountBase getAccountBalance(String accountId, String accessToken) throws IOException {

        PlaidApi plaidClient = getPlaidClient();
        List<String> accountList = new ArrayList<>();
        accountList.add(accountId);
        AccountsBalanceGetRequestOptions requestOptions = new AccountsBalanceGetRequestOptions().accountIds(accountList);
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest()
                .accessToken(accessToken).options(requestOptions);
        Response<AccountsGetResponse> response = plaidClient.accountsBalanceGet(request).execute();
        List<AccountBase> account = response.body().getAccounts();
        return account.get(0);
    }

    public String getAccessToken(String publicToken) throws Exception {

        PlaidApi plaidClient = getPlaidClient();
        ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                .publicToken(publicToken);
        Response<ItemPublicTokenExchangeResponse> response = plaidClient
                .itemPublicTokenExchange(request)
                .execute();
        logger.info(response.toString());
        String accessToken = response.body().getAccessToken();
        logger.info("AccessToken response" + accessToken);
        return accessToken;
    }


}
