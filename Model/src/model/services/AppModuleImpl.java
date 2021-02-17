package model.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Map;

import javax.servlet.http.HttpSession;

import model.services.common.AppModule;

import oracle.adf.share.ADFContext;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;

import oracle.jdbc.OracleTypes;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Feb 02 12:54:40 BDT 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppModuleImpl extends ApplicationModuleImpl implements AppModule {
    /**
     * This is the default constructor (do not remove).
     */
    public static final int VARCHAR2 = OracleTypes.VARCHAR;

    public AppModuleImpl() {
    }

    /**
     * Container's getter for MnjMfgCutlyrcntrlHView1.
     * @return MnjMfgCutlyrcntrlHView1
     */
    public ViewObjectImpl getMnjMfgCutlyrcntrlHView1() {
        return (ViewObjectImpl)findViewObject("MnjMfgCutlyrcntrlHView1");
    }

    /**
     * Container's getter for MnjMfgCutlyrcntrlLView1.
     * @return MnjMfgCutlyrcntrlLView1
     */
    public ViewObjectImpl getMnjMfgCutlyrcntrlLView1() {
        return (ViewObjectImpl)findViewObject("MnjMfgCutlyrcntrlLView1");
    }

    /**
     * Container's getter for MnjMfgCutlyrcntrlDView1.
     * @return MnjMfgCutlyrcntrlDView1
     */
    public ViewObjectImpl getMnjMfgCutlyrcntrlDView1() {
        return (ViewObjectImpl)findViewObject("MnjMfgCutlyrcntrlDView1");
    }

    /**
     * Container's getter for MnjMfgCutlyrcntrlLFkLink1.
     * @return MnjMfgCutlyrcntrlLFkLink1
     */
    public ViewLinkImpl getMnjMfgCutlyrcntrlLFkLink1() {
        return (ViewLinkImpl)findViewLink("MnjMfgCutlyrcntrlLFkLink1");
    }

    /**
     * Container's getter for MnjMfgCutlyrcntrlDFkLink1.
     * @return MnjMfgCutlyrcntrlDFkLink1
     */
    public ViewLinkImpl getMnjMfgCutlyrcntrlDFkLink1() {
        return (ViewLinkImpl)findViewLink("MnjMfgCutlyrcntrlDFkLink1");
    }

    public void createLineAction() {

        ViewObject hvo = getMnjMfgCutlyrcntrlHView1();
        Row hrow = null;
        String minEndBit = null, maxBundleQty = null, minBundleQty = null;
        try {
            hrow = hvo.getCurrentRow();
            minEndBit = hrow.getAttribute("MinEndBit").toString();
            maxBundleQty = hrow.getAttribute("MaxBunQty").toString();
            minBundleQty = hrow.getAttribute("MinBunQty").toString();
        } catch (Exception e) {
            ;
        }

        Row r = getLineRow();

        r.setAttribute("MinEndBit", minEndBit);
        r.setAttribute("MinBunQty", minBundleQty);
        r.setAttribute("MaxBunQty", maxBundleQty);


    }


    public Row getLineRow() {

        ViewObject vo = getMnjMfgCutlyrcntrlLView1();
        Row row = vo.createRow();
        vo.last();
        vo.next();
        vo.insertRow(row);
        row.setNewRowState(Row.STATUS_INITIALIZED);
        return row;
    } //end of createHeader

    public void setRollwhereClause() {
        System.out.println("Wher clause cut no -->" + getSelCutNo());

        ViewObject vo = getRollNosVO1();
        //  vo.setWhereClause("CUT_NO = '"+getSelCutNo()+"'");
        vo.setWhereClause("POC_ID = '" + getSeason() + "'" );
        
        /*+
                          " AND BUYER_ID = '" + getBuyerId() + "'" +
                          " AND STYLE = '" + getStyle() + "'");*/
        vo.executeQuery();
      // System.out.println("the querry for fil rol is:----------"+vo.getQuery()); 
    }

    public void FillRollLines() {


        ViewObject populatevo = getRollNosVO1();
        // populatevo.executeQuery();

        Row[] r = populatevo.getAllRowsInRange();

        for (Row row : r) {

            if (row.getAttribute("Flag") != null &&
                row.getAttribute("Flag").equals("Y")) {
                System.out.println("Flag --->" + row.getAttribute("Flag"));
                popRollAll(row);
            }
        }
    }


    public void popRollAll(Row poprow) {


        Row linerow = getRollLine();

        linerow.setAttribute("RollNo", getPopulateValue(poprow, "RollNo"));
        linerow.setAttribute("RolLengthYds",
                             getPopulateValue(poprow, "MarkLength"));
        linerow.setAttribute("RolLengthInch",
                             getPopulateValue(poprow, "MarkLength2"));
        linerow.setAttribute("Shrinkage",
                             getPopulateValue(poprow, "ShrinkPrcnt"));
        linerow.setAttribute("Shade", getPopulateValue(poprow, "Shade"));
        linerow.setAttribute("SuppRollNo",
                             getPopulateValue(poprow, "SuppRollNo"));
        //linerow.setAttribute("RemPly", getPopulateValue(poprow, "LayRemQty"));
        //linerow.setAttribute("CutQty", getPopulateValue(poprow, "CutQty"));
        //linerow.setAttribute("EndBitYds", getPopulateValue(poprow, "EndBit"));
        //linerow.setAttribute("EndBitInch", getPopulateValue(poprow, "EndBit2"));
        linerow.setAttribute("FabWeight",
                             getPopulateValue(poprow, "FabricContain"));


    } //end of populateLines


    public Row getRollLine() {

        ViewObject vo = getMnjMfgCutlyrcntrlDView1();
        Row row = vo.createRow();
        vo.last();
        vo.next();
        vo.insertRow(row);
        row.setNewRowState(Row.STATUS_INITIALIZED);
        return row;
    } //end of createHeader


    public String getPopulateValue(Row r, String columnName) {

        String value = null;
        try {
            value = r.getAttribute(columnName).toString();
        } catch (Exception e) {
            ;
        }
        return value;
    }

    /**
     * Container's getter for RollNosVO1.
     * @return RollNosVO1
     */
    public ViewObjectImpl getRollNosVO1() {
        return (ViewObjectImpl)findViewObject("RollNosVO1");
    }


    public String getSelCutNo() {

        ViewObject vo = getMnjMfgCutlyrcntrlLView1();
        String cutNo = null;
        try {
            cutNo = vo.getCurrentRow().getAttribute("CutNo").toString();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

        return cutNo;

    }

    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        try {
            // 1. Create a JDBC PreparedStatement for
            st =
 getDBTransaction().createPreparedStatement("begin " + stmt + ";end;", 0);
            if (bindVars != null) {
                // 2. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 3. Set the value of each bind variable in the statement
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            // 4. Execute the statement
            st.executeUpdate();
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 5. Close the statement
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // In StoredProcTestModuleImpl.java

    public void callProcWithOneArgs(String stmnt, String v) {
        callStoredProcedure(stmnt, new Object[] { v });
    }

    public void calcDet() {

        getDBTransaction().commit();

        String stmnt = "mnj_cutlayrcntrl_pkg.get_noof_ply(?)";
        ViewObject vo = getMnjMfgCutlyrcntrlLView1();
        String lineId = vo.getCurrentRow().getAttribute("LineId").toString();
        callProcWithOneArgs(stmnt, lineId);
        vo.executeQuery();

    }


    /**
     * Container's getter for BundleDetailVO1.
     * @return BundleDetailVO1
     */
    public ViewObjectImpl getBundleDetailVO1() {
        return (ViewObjectImpl)findViewObject("BundleDetailVO1");
    }

    /**
     * Container's getter for LineBundleDetVL1.
     * @return LineBundleDetVL1
     */
    public ViewLinkImpl getLineBundleDetVL1() {
        return (ViewLinkImpl)findViewLink("LineBundleDetVL1");
    }


    public void makeBundles() {

        getDBTransaction().commit();

        String stmnt = "MNJ_CUTLAYRCNTRL_PKG.xx_make_bundles(?)";
        ViewObject vo = getMnjMfgCutlyrcntrlLView1();
        String lineId = vo.getCurrentRow().getAttribute("LineId").toString();
        callProcWithOneArgs(stmnt, lineId);

        getBundleDetailVO1().executeQuery();

    }

    public String callMatrix() {

        ViewObject vo = getMnjMfgCutlyrcntrlHView1();

        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        String userId = (String)sessionScope.get("userId");
        String respId = (String)sessionScope.get("respId");
        String respAppl = (String)sessionScope.get("respAppl");

        String headerId =
            vo.getCurrentRow().getAttribute("HeaderId").toString();

        System.out.println("Header id -->" + headerId);
        String status = null;
        String stmt =
            "BEGIN  :1 := MNJ_RATION_PLAN_BUNDLE_REP(:2, :3, :4, :5, :6); end;";
        java.sql.CallableStatement cs =
            getDBTransaction().createCallableStatement(stmt, 1);
        try {
            cs.registerOutParameter(1, OracleTypes.VARCHAR);
            cs.setString(2, headerId);
            cs.setString(3, userId);
            cs.setString(4, respId);
            cs.setString(5, respAppl);
            cs.setInt(6, 1);
            cs.execute();
            status = cs.getString(1);
            cs.close();
        } catch (Exception e) {
            status = e.getMessage();

        }
        System.out.println("User Id matrix-------->" + userId);
        return status;

    }


    public void setSessionValues(String orgId, String userId, String respId,
                                 String respAppl) {


        System.out.println("User Id -------->" + userId);
        if (userId != null) {

            FacesContext fctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = fctx.getExternalContext();
            HttpSession userSession = (HttpSession)ectx.getSession(false);
            userSession.setAttribute("userId", userId);
            userSession.setAttribute("orgId", orgId);
            userSession.setAttribute("respId", respId);
            userSession.setAttribute("respAppl", respAppl);
        }
    }

    public void SplitBundles() {

        getDBTransaction().commit();

        String stmnt = "mnj_cutlayrcntrl_pkg.Split_Bundles(?)";
        ViewObject vo = getBundleDetailVO1();
        String lineId = vo.getCurrentRow().getAttribute("DetailId").toString();
        callProcWithOneArgs(stmnt, lineId);

        getBundleDetailVO1().executeQuery();

    }

    public String getBuyerId() {

        ViewObject vo = getMnjMfgCutlyrcntrlHView1();
        String BuyerId = null;
        try {
            BuyerId = vo.getCurrentRow().getAttribute("BuyerId").toString();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

        return BuyerId;

    }


    public String getStyle() {

        ViewObject vo = getMnjMfgCutlyrcntrlHView1();
        String Style = null;
        try {
            Style = vo.getCurrentRow().getAttribute("Style").toString();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

        return Style;

    }


    public String getSeason() {

        ViewObject vo = getMnjMfgCutlyrcntrlHView1();
        String Season = null;
        try {
            Season = vo.getCurrentRow().getAttribute("PocId").toString();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

        return Season;

    }

    public void save() {
        getDBTransaction().commit();
        AvgConsumption_Calc();
    }

    public void AvgConsumption_Calc() {

        System.out.println("In AvgConsumption_Calc method.... ");

        Double ActualConsumption = 0.0;
        Double Total_ActualConsumption = 0.0;
        Double Avg_ActualConsumption = 0.0;

        getDBTransaction().commit();
        ViewObject hvo = this.getMnjMfgCutlyrcntrlHView1();
        ViewObject lvo = this.getMnjMfgCutlyrcntrlLView1();

        int rowtotal = 0;

        try {
            rowtotal = lvo.getRowCount();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            rowtotal = 0;
        }

        System.out.println("Total Rows at Line are ..." + rowtotal);
        RowSetIterator lvoit = lvo.createRowSetIterator("lvoit");

        while (lvoit.hasNext()) {

            Row linesNext = lvoit.next();
            String LineId = null;
            Avg_ActualConsumption = 0.00;

            try {
                LineId = linesNext.getAttribute("LineId").toString();
            } catch (Exception e) {
                ;
            }

            try {
                ActualConsumption =
                        Double.parseDouble(linesNext.getAttribute("ActualConsumption").toString());
                System.out.println("ActualConsumption Value is... " +
                                   ActualConsumption);
            } catch (Exception ee) {
                ActualConsumption = 0.0;
                System.out.println("Exception ActualConsumption");
            }

            Total_ActualConsumption =
                    Total_ActualConsumption + ActualConsumption;
            System.out.println("Total Actual Consumption " +
                               Total_ActualConsumption);

        }
        lvoit.closeRowSetIterator();

        if (rowtotal != 0) {
            Avg_ActualConsumption = Total_ActualConsumption / rowtotal;
            hvo.getCurrentRow().setAttribute("AvgActualConsumption",
                                             Avg_ActualConsumption);
        } else {
            hvo.getCurrentRow().setAttribute("AvgActualConsumption", 0);
            System.out.println("In else....");
        }
        System.out.println("Save.....");

        this.getDBTransaction().commit();
    }

    public String Calculate_Cons(int HeaderId) {

        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        String user = (String)sessionScope.get("userId");
        String orgId = (String)sessionScope.get("orgId");
        String respId = (String)sessionScope.get("respId");
        String respAppl = (String)sessionScope.get("respAppl");

        String status = null;
        status =
                (String)callStoredFunction(VARCHAR2, "mnj_calculate_consumption(?, ?, ?, ?, ?)",
                                           new Object[] { HeaderId, orgId,
                                                          respId, user,
                                                          respAppl });

        return status;
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt,
                                        Object[] bindVars) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallabledStatement
            st =
 getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    ;
                }
            }
        }

    }

    /**
     * Container's getter for MnjCutlyrcntrlWorkingpwrLView1.
     * @return MnjCutlyrcntrlWorkingpwrLView1
     */
    public ViewObjectImpl getMnjCutlyrcntrlWorkingpwrLView1() {
        return (ViewObjectImpl)findViewObject("MnjCutlyrcntrlWorkingpwrLView1");
    }

    /**
     * Container's getter for MnjCutlyrcntrlWorkingpwrR01Link1.
     * @return MnjCutlyrcntrlWorkingpwrR01Link1
     */
    public ViewLinkImpl getMnjCutlyrcntrlWorkingpwrR01Link1() {
        return (ViewLinkImpl)findViewLink("MnjCutlyrcntrlWorkingpwrR01Link1");
    }

    /**
     * Container's getter for MnjCutlyrcntrlOffstandardLView1.
     * @return MnjCutlyrcntrlOffstandardLView1
     */
    public ViewObjectImpl getMnjCutlyrcntrlOffstandardLView1() {
        return (ViewObjectImpl)findViewObject("MnjCutlyrcntrlOffstandardLView1");
    }

    /**
     * Container's getter for MnjCutlyrcntrlOffstandarR01Link1.
     * @return MnjCutlyrcntrlOffstandarR01Link1
     */
    public ViewLinkImpl getMnjCutlyrcntrlOffstandarR01Link1() {
        return (ViewLinkImpl)findViewLink("MnjCutlyrcntrlOffstandarR01Link1");
    }

    /**
     * Container's getter for MnjCutlyrcntrlOffstandardDView1.
     * @return MnjCutlyrcntrlOffstandardDView1
     */
    public ViewObjectImpl getMnjCutlyrcntrlOffstandardDView1() {
        return (ViewObjectImpl)findViewObject("MnjCutlyrcntrlOffstandardDView1");
    }

    /**
     * Container's getter for MnjCutlyrcntrlOffstandarFkLink1.
     * @return MnjCutlyrcntrlOffstandarFkLink1
     */
    public ViewLinkImpl getMnjCutlyrcntrlOffstandarFkLink1() {
        return (ViewLinkImpl)findViewLink("MnjCutlyrcntrlOffstandarFkLink1");
    }
}//end of class
