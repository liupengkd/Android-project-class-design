package com.pss.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pss.action.CustomerAction;
import com.pss.action.DeliveryMasterAction;
import com.pss.action.LoseSpillAction;
import com.pss.action.ProductAction;
import com.pss.action.PurchaseAction;
import com.pss.action.StockAction;
import com.pss.action.SupplierAction;
import com.pss.action.UsersAction;

public class PSSControllerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public PSSControllerServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的编码格式
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 设置响应的MIME类型
		response.setContentType("text/html;charset=utf-8");
		// 通过请求得到请求的URI
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		// 按“/”将请求URI拆分为数组
		String[] strUri = requestURI.split("/");
		// 声明一个字符串变量，用来存储请求动作
		String actionStr = "";
		// 循环遍历数组，判断其中是否包含“do” 由于所有的请求都是以“.do”为后缀，截取“.”之前的字符串
		for (String str : strUri) {
			if (str.contains("do")) {
				actionStr = str.substring(0, str.indexOf("."));
			}
		}
		System.out.println(actionStr);
		String result = "";
		if (actionStr.equals("StockAlarm")) {
			StockAction stockAction = new StockAction();
			result = stockAction.getAlarmProduct(request, response);
		} else if (actionStr.equals("getProductNameOrId")) {
			StockAction stockAction = new StockAction();
			result = stockAction.getALLProductList(request, response);
		} else if (actionStr.equals("getAllLoseSpills")) {
			LoseSpillAction loseSpillAction = new LoseSpillAction();
			result = loseSpillAction.getAllLoseSpills(request, response);
		} else if (actionStr.equals("getALLProductList")) {
			StockAction stockAction = new StockAction();
			result = stockAction.getALLProductList(request, response);
		} else if (actionStr.equals("getLoseSpillByName")) {
			LoseSpillAction loseSpillAction = new LoseSpillAction();
			result = loseSpillAction.getLoseSpillByName(request, response);
		} else if (actionStr.equals("getProductByName")) {
			StockAction stockAction = new StockAction();
			result = stockAction.getProductByName(request, response);
		} else if (actionStr.equals("addLoseSpill")) {
			StockAction stockAction = new StockAction();
			result = stockAction.isExistProduct(request, response);
		} else if (actionStr.equals("getAllUsers")) {
			UsersAction usersAction = new UsersAction();
			result = usersAction.getAllUsers(request, response);
		} else if (actionStr.equals("addUsers")) {
			UsersAction usersAction = new UsersAction();
			result = usersAction.addUsers(request, response);
		} else if (actionStr.equals("deleteUser")) {
			UsersAction usersAction = new UsersAction();
			result = usersAction.deleteUser(request, response);
		} else if (actionStr.equals("LoginByNameAndPwd")) {
			UsersAction usersAction = new UsersAction();
			result = usersAction.LoginByNameAndPwd(request, response);
		} else if (actionStr.equals("resetPassword")) {
			UsersAction userAction = new UsersAction();
			result = userAction.resetPassword(request, response);
		} else if (actionStr.equals("updateUserInfo")) {
			UsersAction usersAction = new UsersAction();
			result = usersAction.updateUserInfo(request, response);
		} else if (actionStr.equals("updatePassword")) {
			UsersAction userAction = new UsersAction();
			result = userAction.updatePassword(request, response);
		} else if (actionStr.equals("getUsersByNameOrId")) {
			UsersAction usersAction = new UsersAction();
			result = usersAction.getUsersByNameOrId(request, response);
		} else if (actionStr.equals("SelectAllProduct")) {
			ProductAction pa = new ProductAction();
			result = pa.SelectAllProduct(request, response);

		} else if (actionStr.equals("AddProduct")) {
			ProductAction pa = new ProductAction();
			result = pa.AddProiduct(request, response);
		} else if (actionStr.equals("deleteProduct")) {
			ProductAction pa = new ProductAction();
			result = pa.DeleteProduct(request, response);
		} else if (actionStr.equals("UpdateProduct")) {
			ProductAction pa = new ProductAction();
			result = pa.UpdateProduct(request, response);
		} else if (actionStr.equals("SelectAllCustomer")) {
			CustomerAction ca = new CustomerAction();
			result = ca.SelectAllCustomer(request, response);
		} else if (actionStr.equals("AddCustomer")) {
			CustomerAction ca = new CustomerAction();
			result = ca.AddCustomer(request, response);
		} else if (actionStr.equals("deleteCustomer")) {
			CustomerAction ca = new CustomerAction();
			result = ca.deleteCustomer(request, response);
		} else if (actionStr.equals("UpdateCustomer")) {
			CustomerAction ca = new CustomerAction();
			result = ca.UpateCustomer(request, response);
		} else if (actionStr.equals("getCustomerByName")) {
			CustomerAction ca = new CustomerAction();
			result = ca.getCustomerByName(request, response);
		} else if (actionStr.equals("getCustomerNameOrId")) {
			CustomerAction ca = new CustomerAction();
			result = ca.SelectAllCustomer(request, response);
		} else if (actionStr.equals("getProductByNameID")) {
			ProductAction pa = new ProductAction();
			result = pa.getProductByNameorID(request, response);
		} else if (actionStr.equals("SelectAllSupplier")) {
			SupplierAction pa = new SupplierAction();
			result = pa.selectAllSupplier(request, response);
		} else if (actionStr.equals("SelectSupplierOneName")) {
			SupplierAction su = new SupplierAction();
			result = su.getOneSupplierName(request, response);
		} else if (actionStr.equals("AddSupplier")) {
			SupplierAction pa = new SupplierAction();
			result = pa.AddSupplier(request, response);
		} else if (actionStr.equals("getSupplierByName")) {
			SupplierAction pa = new SupplierAction();
			result = pa.getSupplierByName(request, response);
		} else if (actionStr.equals("updateSupplier")) {
			SupplierAction pa = new SupplierAction();
			result = pa.updateSupplier(request, response);
		} else if (actionStr.equals("deleteSupplier")) {
			SupplierAction pa = new SupplierAction();
			result = pa.deleteSupplier(request, response);
		} else if (actionStr.equals("addDelivery")) {
			DeliveryMasterAction dma = new DeliveryMasterAction();
			result = dma.addDeliveryMaster(request, response);
		} else if (actionStr.equals("getAllDelivery")) {
			DeliveryMasterAction dma = new DeliveryMasterAction();
			result = dma.getAllDelivery(request, response);
		} else if (actionStr.equals("getAllDeliveryDetail")) {
			DeliveryMasterAction dma = new DeliveryMasterAction();
			result = dma.getAllDeliveryByDeliveryID(request, response);
		} else if (actionStr.equals("getOneDelivery")) {
			DeliveryMasterAction dma = new DeliveryMasterAction();
			result = dma.getOneDelivery(request, response);
		}else if (actionStr.equals("purchaseinsert")) {
			PurchaseAction purchaseAction = new PurchaseAction();
			result = purchaseAction.insert(request, response);
		}else if (actionStr.equals("purchasegetallru")) {
			PurchaseAction purchaseAction = new PurchaseAction();
			result = purchaseAction.getAllRu(request, response);
		}else if (actionStr.equals("purchasegetalltui")) {
			PurchaseAction purchaseAction = new PurchaseAction();
			result = purchaseAction.getAllTui(request, response);
		}else if (actionStr.equals("purchasegetonebyidru")) {
			PurchaseAction purchaseAction = new PurchaseAction();
			result = purchaseAction.getOneByIdRu(request, response);
		}else if (actionStr.equals("purchasegetonebyidtui")) {
			PurchaseAction purchaseAction = new PurchaseAction();
			result = purchaseAction.getOneByIdTui(request, response);
		}else if (actionStr.equals("GetSupplierOneName")) {
			SupplierAction su = new SupplierAction();
			result = su.getOneSupplierName(request, response);
		}else if (actionStr.equals("GetSupplierOneNames")) {
			SupplierAction su = new SupplierAction();
			result = su.getOneSupplierNames(request, response);
		}else if (actionStr.equals("SelectAllSupplier")) {
			SupplierAction pa = new SupplierAction();
			result = pa.selectAllSupplier(request, response);
		}else if (actionStr.equals("GetOneProductName")) {
			ProductAction pa = new ProductAction();
			result = pa.getOneProductName(request, response);
		}else if (actionStr.equals("GetAllProduct")) {
			ProductAction pa = new ProductAction();
			result = pa.SelectAllProduct(request, response);
		}else if (actionStr.equals("addPurchase")) {
			PurchaseAction pa = new PurchaseAction();
			result = pa.insert(request, response);
		}else if(actionStr.equals("GetProductByName")){
			ProductAction pa=new ProductAction();
			result=pa.getProductByName(request, response);
		}else if (actionStr.equals("getAutoUserId")) {
			UsersAction usersAction=new UsersAction();
		    result=usersAction.getAutoUserId(request, response);
		}else if (actionStr.equals("getAutoLoseSpillId")) {
			LoseSpillAction loseSpillAction=new LoseSpillAction();
			result=loseSpillAction.getAutoLoseSpillId(request, response);
		}else if (actionStr.equals("getAutoProductId")) {
			ProductAction pa=new ProductAction();
			result=pa.getAutoProductId(request, response);
		}
	    //System.out.println(result);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
