/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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