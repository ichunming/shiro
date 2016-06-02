package com.yimeicloud.study.shiro;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        String str = "aTom";
        String salt = "123";
        String md5Hash = new Md5Hash(str, salt).toString();
        System.out.println(md5Hash);
        
        DefaultHashService hashService = new DefaultHashService();
        /*// 默认算法
        hashService.setHashAlgorithmName("SHA-512");
        // 私盐
        hashService.setPrivateSalt(new SimpleByteSource("123"));
        // 是否生成公盐
        hashService.setGeneratePublicSalt(false);
        // 用于生成公盐
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
        // 生成hash值的迭代次数
        hashService.setHashIterations(1);*/
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        
        HashRequest request = new HashRequest.Builder()
        		.setAlgorithmName("MD5")
        		.setSource(ByteSource.Util.bytes("aTom"))
        		.setSalt(ByteSource.Util.bytes("aTom"))
        		.setIterations(1)
        		.build();
        
        String hex = hashService.computeHash(request).toString();
        System.out.println(hex);
        System.out.println(salt2);
        
        // *******
        String algorithmName = "md5";  
        String username = "aTom";  
        String password = "aTom";  
        String salt1 = username;  
        //String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        
        int hashIterations = 1;  

        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);  
        String encodedPassword = hash.toHex();
        System.out.println("aTom");
        System.out.println(encodedPassword);
        System.out.println(salt2);
        
        
        SecureRandomNumberGenerator randomNumberGenerator =  
        	     new SecureRandomNumberGenerator();  
        	randomNumberGenerator.setSeed("123".getBytes());
        System.out.println(randomNumberGenerator.nextBytes().toHex());
    }
}
