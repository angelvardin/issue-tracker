package com.bugtracker.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.bugtracker.entity.*;





public class HibernateUtils {
	private static SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration().configure();

			configuration.addAnnotatedClass(CommentModel.class);
			configuration.addAnnotatedClass(IssueModel.class);
			configuration.addAnnotatedClass(ProjectModel.class);
			configuration.addAnnotatedClass(RoleModel.class);
			configuration.addAnnotatedClass(UserModel.class);

			StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

			serviceRegistryBuilder.applySettings(configuration.getProperties());

			StandardServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception ex) {
			System.out.println("-------------------------------------");
			System.out.println(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}

