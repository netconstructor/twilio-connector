package com.twilio.sdk.verbs;

import java.util.ArrayList;


/*
Copyright (c) 2008 Twilio, Inc.

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
*/

public class TwiMLResponse extends Verb {

    public TwiMLResponse() {
        super(V_RESPONSE, null);
        this.allowedVerbs = new ArrayList<String>();
        this.allowedVerbs.add(V_GATHER);
        this.allowedVerbs.add(V_RECORD);
        this.allowedVerbs.add(V_DIAL);
        this.allowedVerbs.add(V_SAY);
        this.allowedVerbs.add(V_PLAY);
        this.allowedVerbs.add(V_REDIRECT);
        this.allowedVerbs.add(V_REJECT);
        this.allowedVerbs.add(V_HANGUP);
        this.allowedVerbs.add(V_SMS);
    }

}

