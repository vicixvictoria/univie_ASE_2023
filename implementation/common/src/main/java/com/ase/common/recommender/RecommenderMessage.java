package com.ase.common.recommender;

import java.io.Serializable;
import java.util.List;

public record RecommenderMessage(String eventID, List<String> userList) implements Serializable {

}
