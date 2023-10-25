package szu.se.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.se.dao
 * @Author: Origami
 * @Date: 2023/10/2 10:06
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser {
    Integer id;
    String username;
    String password;
}
