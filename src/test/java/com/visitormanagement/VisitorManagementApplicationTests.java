//package com.visitormanagement;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.visitormanagement.models.Visitor;
//import com.visitormanagement.models.VisitorLog;
//import com.visitormanagement.repositories.VisitorLogRepository;
//import com.visitormanagement.services.VisitorService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class VisitorManagementApplicationTests {
//
//	@Autowired
//	private VisitorService visitorService;
//	
//	@MockBean
//	private VisitorLogRepository visitorLogRepo;
//	
//	@Test
//	public void getAllUsersLogByMeTest(){
//		Visitor visitor = new Visitor("Ochuko", "07062931318", "Lagos", "Male", "admin");
//		Visitor visitor2 = new Visitor("Ochu", "07066931318", "Ghana", "Female", "admin");
//		when(visitorLogRepo.findBySignedBy("admin_name")).thenReturn(Stream
//		.of(new VisitorLog("Mr. Etiene", "official", "TAG075" , "admin", visitor), 
//             new VisitorLog("Mr. Akeni", "private", "TAG076" , "admin", visitor2))
//				.collect(Collectors.toList()));
//		
//		assertEquals(2, visitorService.findAllVisitorsLogByMe("admin_name").size());
//		
//	}
//	
//	
//	
//
//	
//}
