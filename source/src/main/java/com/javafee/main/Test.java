package com.javafee.main;

import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.LibraryWorker;
import com.javafee.hibernate.dto.library.Worker;

public class Test {
	public static void main(String[] args) {
		HibernateUtil.getSession().beginTransaction();
		// City testCity = new City();
		// testCity.setName("Gliwice");
		// testCity.setPostalCode("42-700");

		// Client sykanska = new Client();
		// sykanska.setLogin("SyKanska");
		// sykanska.setName("Sylwia");
		// sykanska.setSurname("Kańska");
		// sykanska.setAddress("ul. Gogolińska 27/14");
		// sykanska.setPassword("a");

		// Worker makolek = new Worker();
		// makolek.setLogin("MaKolek");
		// makolek.setName("Marek");
		// makolek.setSurname("Kołek");
		// makolek.setAddress("ul. Katowice 6B");
		// makolek.setPassword("b");
		// makolek.setSalary(new BigDecimal(2300));
		//
		// LibraryData ld = new LibraryData();
		// ld.setAddress("ul. Wolności 173");
		// ld.setBranch("Filia nr 11");
		// HibernateUtil.getSession().save(ld);
		//
		// LibraryWorker lw = new LibraryWorker();
		// lw.setIsAccountant(true);
		// lw.setLibData(ld);
		// lw.setWorker(makolek);

		// testWorker
		// -------------------------------------------------------------------------------------------------
		// Worker testWorker = new Worker();
		// testWorker.setLogin("MiMichalski");
		// testWorker.setPassword("MiMichalski");
		// testWorker.setName("Michał");
		// testWorker.setSurname("Michalski");
		// testWorker.setSex('M');
		// testWorker.setPeselNumber("88121318933");
		// testWorker.setRegistered(false);
		// testWorker.setAddress("ul. Kościuszki 6B/12");
		// testWorker.setSalary(BigDecimal.valueOf(2100.55));

		// testLibData
		// -------------------------------------------------------------------------------------------------
		// LibraryData testLibData = new LibraryData();
		// testLibData.setName("Miejska Biblioteka Publiczna w Zabrzu");
		// testLibData.setBranch("Filia 11");
		// testLibData.setAddress("ul. Wolności 177");
		// HibernateUtil.getSession().save(testLibData);

		// testLibData1
		// -------------------------------------------------------------------------------------------------
		// LibraryData testLibData1 = new LibraryData();
		// testLibData1.setName("Miejska Biblioteka Publiczna w Zabrzu");
		// testLibData1.setBranch("Filia 9");
		// testLibData1.setAddress("ul. Roosvelta 13");
		// HibernateUtil.getSession().save(testLibData1);

		// first employment of testWorker in testLibData as non-accountant
		// -------------------------------------------------------------------------------------------------
		// LibraryWorker libraryWorker = new LibraryWorker();
		// libraryWorker.setWorker(testWorker);
		// libraryWorker.setLibData(testLibData);
		// libraryWorker.setIsAccountant(false);

		// second employment of testWorker in testLibData1 accountant
		// -------------------------------------------------------------------------------------------------
		// LibraryWorker libraryWorker1 = new LibraryWorker();
		// libraryWorker1.setWorker(testWorker);
		// libraryWorker1.setLibData(testLibData1);
		// libraryWorker1.setIsAccountant(true);
		//
		// testWorker.getLibraryWorker().add(libraryWorker);
		// testWorker.getLibraryWorker().add(libraryWorker1);

		// HibernateUtil.getSession().save(sykanska);
		// HibernateUtil.getSession().save(testLibData);
		// HibernateUtil.getSession().save(testLibData1);

		// HibernateUtil.getSession().save(testWorker);

		// HibernateUtil.commitTransaction();
		// List<City> city = HibernateUtil.getSession().createQuery("from City where
		// name like 'Gliwice'").list();

		Worker worker = (Worker) HibernateUtil.getSession().getNamedQuery("Worker.checkIfWorkerLoginExist")
				.setParameter("login", "MiMichalski").uniqueResult();
		@SuppressWarnings({"unused"})
		LibraryWorker libraryWorker = (LibraryWorker) HibernateUtil.getSession()
				.getNamedQuery("LibraryWorker.checkIfLibraryWorkerHiredExist")
				.setParameter("idWorker", worker.getIdUserData()).uniqueResult();

		HibernateUtil.getSession().close();

		// System.out.println(city.get(0).getName());
	}
}
