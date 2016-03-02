package com.zhy.m.permission;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ProxyInfo
{
    private String packageName;
    private String proxyClassName;
    private TypeElement typeElement;

    Map<Integer, String> grantMethodMap = new HashMap<>();
    Map<Integer, String> deniedMethodMap = new HashMap<>();
    Map<Integer, String> rationaleMethodMap = new HashMap<>();

    public static final String PROXY = "PermissionProxy";

    public ProxyInfo(Elements elementUtils, TypeElement classElement)
    {
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        //classname
        String className = ClassValidator.getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
    }


    public String getProxyClassFullName()
    {
        return packageName + "." + proxyClassName;
    }

    public String generateJavaCode()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.zhy.m.permission.*;\n");
        builder.append('\n');

        builder.append("public class ").append(proxyClassName).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getSimpleName() + ">");
        builder.append(" {\n");

        generateMethods(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();

    }


    private void generateMethods(StringBuilder builder)
    {

        generateGrantMethod(builder);
        generateDeniedMethod(builder);
        generateRationaleMethod(builder);


    }

    private void generateRationaleMethod(StringBuilder builder)
    {
        builder.append("@Override\n ");
        builder.append("public void rationale(" + typeElement.getSimpleName() + " source , int requestCode) {\n");
        builder.append("switch(requestCode) {");
        for (int code : rationaleMethodMap.keySet())
        {
            builder.append("case " + code + ":");
            builder.append("source." + rationaleMethodMap.get(code) + "();");
            builder.append("break;");
        }

        builder.append("}");
        builder.append("  }\n");

        ///

        builder.append("@Override\n ");
        builder.append("public boolean needShowRationale(int requestCode) {\n");
        builder.append("switch(requestCode) {");
        for (int code : rationaleMethodMap.keySet())
        {
            builder.append("case " + code + ":");
            builder.append("return true;");
        }
        builder.append("}\n");
        builder.append("return false;");

        builder.append("  }\n");
    }

    private void generateDeniedMethod(StringBuilder builder)
    {
        builder.append("@Override\n ");
        builder.append("public void denied(" + typeElement.getSimpleName() + " source , int requestCode) {\n");
        builder.append("switch(requestCode) {");
        for (int code : deniedMethodMap.keySet())
        {
            builder.append("case " + code + ":");
            builder.append("source." + deniedMethodMap.get(code) + "();");
            builder.append("break;");
        }

        builder.append("}");
        builder.append("  }\n");
    }

    private void generateGrantMethod(StringBuilder builder)
    {
        builder.append("@Override\n ");
        builder.append("public void grant(" + typeElement.getSimpleName() + " source , int requestCode) {\n");
        builder.append("switch(requestCode) {");
        for (int code : grantMethodMap.keySet())
        {
            builder.append("case " + code + ":");
            builder.append("source." + grantMethodMap.get(code) + "();");
            builder.append("break;");
        }

        builder.append("}");
        builder.append("  }\n");
    }

    public TypeElement getTypeElement()
    {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement)
    {
        this.typeElement = typeElement;
    }


}