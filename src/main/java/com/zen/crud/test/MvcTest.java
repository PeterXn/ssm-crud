package com.zen.crud.test;

import com.github.pagehelper.PageInfo;
import com.zen.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 使用Spring测试模块提供的测试请求功能,测试crud请求的正确性
 * <p>
 * ContextConfiguration获取WEB-INF下的文件?
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:dispatcherServlet-*.xml"})
public class MvcTest {

    @Autowired
    WebApplicationContext context;

    //虚拟mvc请求，获取到处理结果
    MockMvc mockMvc;

    //@Autowired
    //EmployeeController employeeController;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
                .andReturn();

        //请求成功后，请求域中会有pageInfo
        MockHttpServletRequest request = result.getRequest();
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
        //验证
        System.out.println("当前页码：" + pageInfo.getPageNum());
        System.out.println("总页码：" + pageInfo.getPages());
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码:");
        int[] nums = pageInfo.getNavigatepageNums();
        for (int i : nums) {
            System.out.print(" " + i);
        }

        //获取员工数据
        List<Employee> list = pageInfo.getList();
        for (Employee employee : list) {
            System.out.println("ID: " + employee.getEmpId() + "====>Name: " + employee.getEmpName());
        }
    }

    @Test
    public void test01() {
        String[] names = context.getBeanDefinitionNames();
        for (int i = 0; i < names.length - 1; i++) {
            System.out.println("bean====" + names[i]);

        }

        System.out.println("==================================================");
        //System.out.println("employeeController====>"+ employeeController);

    }
}
