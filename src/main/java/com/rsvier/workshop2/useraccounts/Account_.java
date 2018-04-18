package com.rsvier.workshop2.useraccounts;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.rsvier.workshop2.useraccounts.Account.OwnerType;

@StaticMetamodel (Account.class)
public class Account_ {
	public static volatile SingularAttribute<Account, Long> id;
	public static volatile SingularAttribute<Account, String> username;
	public static volatile SingularAttribute<Account, String> encryptedPassword;
	public static volatile SingularAttribute<Account, OwnerType> ownerType;
	public static volatile SingularAttribute<Account, String> salt;
}
