package com.rcipe.service.recipe.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rcipe.commons.Search;
import com.rcipe.service.comment.CommentService;
import com.rcipe.service.domain.Ingredient;
import com.rcipe.service.domain.Recipe;
import com.rcipe.service.recipe.RecipeDAO;
import com.rcipe.service.recipe.RecipeService;

@Service("recipeServiceImpl")
public class RecipeServiceImpl  implements RecipeService{

	@Autowired
	@Qualifier("recipeDAOImpl")
	private RecipeDAO recipeDAO;
	
	@Autowired
	@Qualifier("commentServiceImpl")
	private CommentService commentServcie;
	
	public RecipeServiceImpl() {
		System.out.println(getClass()+"start......");
	}

	@Override
	public Recipe getStar(Recipe recipe) throws Exception {
		return recipeDAO.getStar(recipe);
	}

	@Override
	public List<Ingredient> getIngredientList(String keyword) throws Exception {
		return recipeDAO.getIngredientList(keyword);
	}

	@Override
	public boolean insertIngredient(String ingredientName) throws Exception {
		return recipeDAO.insertIngredient(ingredientName);
	}

	@Override
	public String insertStar(Recipe recipe) throws Exception {
		return recipeDAO.insertStar(recipe);
	}

	@Override
	public Recipe insertRecipe(Recipe recipe) throws Exception {
		return recipeDAO.insertRecipe(recipe);
	}

	@Override
	public int updateRecipe(Recipe recipe) throws Exception {
		return recipeDAO.updateRecipe(recipe);
	}

	@Override
	public boolean insertRcpIng(List<Ingredient> list) throws Exception {
		return recipeDAO.insertRcpIng(list);
	}


	@Override
	public boolean deleteRecipe(int recipeNo) throws Exception {
		commentServcie.deleteRecipeCmtList(recipeNo);
		return recipeDAO.deleteRecipe(recipeNo) == 1 ? true : false;
	}

	@Override
	public Recipe getRecipe(int recipeNo) throws Exception {
		Recipe recipe=recipeDAO.getRecipe(recipeNo);
		recipeDAO.updateRecipeCount(recipeNo);
		recipe.setCommentList(commentServcie.getRecipeCmtList(recipe.getRecipeNo()));
		return recipe;
	}

	@Override
	public boolean deleteRcpIng(int recipeNo) throws Exception {
		// TODO Auto-generated method stub
		return recipeDAO.deleteRcpIng(recipeNo) == 1 ? false : true;
	}

	@Override
	public Map<String, Object> getRecipeList(Search search) throws Exception {
		
		String searchKeyword = search.getSearchKeyword();
		int totalCount;
		List<Recipe> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if(searchKeyword==null){
			list = recipeDAO.getRecipeList(search);		// order by 없음. 
			totalCount = recipeDAO.getTotalCount(search);
		}else{
			if(searchKeyword.contains(",")==true){
				String[] searchWordList = searchKeyword.split(",");
				List<String> searchIngredients = new ArrayList<String>();
				for(int i=0;i<searchWordList.length;i++){
					searchIngredients.add(searchWordList[i]);
				}
				list = recipeDAO.getRecipeListIngredients(searchIngredients);
				totalCount = list.size();
				
				if(totalCount>search.getEndRowNum()){
					list = list.subList(search.getStartRowNum()-1, search.getEndRowNum());
				}else{
					list = list.subList(search.getStartRowNum()-1, totalCount);
				}
			}else{
				list = recipeDAO.getRecipeList(search); // nickname,title 기준으로 가져오는 것
				totalCount = recipeDAO.getTotalCount(search);
			}
		}
		
		//메인 페이지는 COUNT가 필요없다. 50개만 가져올거임
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		return map;
	}

}
