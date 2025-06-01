package org.example.bonus;

import javassist.*;

public class BytecodeModifier {

    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();

        pool.insertClassPath("C:\\Users\\Bogdan S\\OneDrive\\Documents\\GitHub\\JavaSem4\\Lab12\\target\\classes");

        CtClass ctClass = pool.get("org.example.bonus.TargetClass");

        CtMethod method = ctClass.getDeclaredMethod("greet");

        method.insertBefore(" for(int i = 0; i < 10; i++) { System.out.println(\"AAAA\" +  i); } ");

        Class<?> modifiedClass = ctClass.toClass();

        Object obj = modifiedClass.getDeclaredConstructor().newInstance();
        modifiedClass.getMethod("greet").invoke(obj);
    }
}
