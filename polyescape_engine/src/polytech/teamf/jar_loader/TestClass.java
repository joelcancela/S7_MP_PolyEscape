package polytech.teamf.jar_loader;/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	Copyright (c) Non, Inc. 1999 -- All Rights Reserved

PACKAGE:	JavaWorld
FILE:		TestClass.java

AUTHOR:		John D. Mitchell, Mar  3, 1999

REVISION HISTORY:
	Name	Date		Description
	----	----		-----------
	JDM	99.03.03   	Initial version.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/


// Standard Java Core packages:

import java.lang.* ;


/**
 ** This is just a simple, "Hello World" class which dumps it's message to
 ** stdout which we can use to show that we can load classes via the
 ** JarResources.getClass() method.
 <br><br>
 **
 ** @author	John D. Mitchell, Non, Inc., Mar  3, 1999
 **
 ** @version 0.5
 **
 **/

public class TestClass
{

    public TestClass()
    {
        System.out.println ("TestClass:  Running in constructor...");
    }

    public void doSomething()
    {
        System.out.println ("TestClass:  Did something special!");
    }

}	// End of class @class.