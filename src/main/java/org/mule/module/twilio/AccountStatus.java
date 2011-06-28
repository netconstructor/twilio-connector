package org.mule.module.twilio;

/**
 * Represents the status of a Twilio account.
 * <p/>
 * These enum constants can be used as a search parameter or to update the account status.
 *
 * @see {@link TwilioConnector#getAllAccountsDetails(AccountStatus, String)}
 * @see {@link TwilioConnector#updateAccount(String, AccountStatus, String)}
 */
public enum AccountStatus {

    SUSPENDED, ACTIVE, CLOSED
}