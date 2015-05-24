
package com.example.alessandro.computergraphicsexample.oldVersion.library.shadow.system;

/**
 * Something which can be initialized
 * 
 * @pattern interface
 * 
 * @author Alessandro Martinelli 
 */
public interface SFInitiable {
	/**
	 * initialize this SFInitiable
	 */
	public void init();
	
	/**
	 * Destroy this SFInitiable
	 */
	public void destroy();
}