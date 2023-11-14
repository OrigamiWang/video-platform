package szu;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import szu.model.Role;
import szu.model.User;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ManageApplication.class)
@AutoConfigureMockMvc
public class TestRoleController {
    @Resource
    private MockMvc mockMvc;

    @Test
    @DisplayName("增加角色接口测试")
    public void test1() throws Exception {
        Role role = new Role();
        role.setName("测试角色");
        role.setDescribe("这个角色用来测试");
        mockMvc.perform(MockMvcRequestBuilders.post("/mg/role/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJsonStr(role))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("查询所有角色接口测试")
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mg/role/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }
    @Test
    @DisplayName("删除角色接口测试")
    public void test3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/mg/role/delete")
                        .param("roleName","测试角色")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }
    @Test
    @DisplayName("增加权限接口测试")
    public void test4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mg/role/permission/add")
                        .param("permission","测试权限")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("查询所有权限接口测试")
    public void test8() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mg/role//permission/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    @DisplayName("删除权限接口测试")
    public void test5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/mg/role/permission/delete")
                        .param("permission","测试权限")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("200"))
                .andDo(print());
    }

    @Test
    public void test7() throws Exception {
        String a = "1,3,4,5,6,7";
        String[] split = a.split(",");
        List<Integer> integerList = Arrays.stream(split)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println(integerList);
    }


}
