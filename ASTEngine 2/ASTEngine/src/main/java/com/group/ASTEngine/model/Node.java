package com.group.ASTEngine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
  // Node node = m/apper.convertValue(singleObject, POJO.class);

    private String type;
    private Node left;
    private Node right;
    private Object value;
}
