package view.backing_bean;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class LayBeanClass {
    private RichInputComboboxListOfValues planNoBind;
    private RichTable rollTable;
    private RichInputText timeCal;
    private RichInputDate startTime;
    private RichInputDate endTime;
    private RichInputText fromPieceNoBinding;
    private RichInputText toPieceNoBinding;
    private RichOutputText totalBundleQuantityBinding;

    // By Sabih
    ViewObject MnjMfgCutlyrcntrlLView1;
    ViewObject BundleDetailVO1;
    private RichTable bundleTable;
    private RichInputListOfValues cutPlanNumber;
    private RichInputText headerId;
    private RichPanelFormLayout panelformlayout_H;

    public LayBeanClass() {

        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("AppModuleDataControl"); //
        ApplicationModule am = dc.getApplicationModule();
        MnjMfgCutlyrcntrlLView1 = am.findViewObject("MnjMfgCutlyrcntrlLView1");
        BundleDetailVO1 = am.findViewObject("BundleDetailVO1");


    }

    public void setPlanNoBind(RichInputComboboxListOfValues planNoBind) {
        this.planNoBind = planNoBind;
    }

    public RichInputComboboxListOfValues getPlanNoBind() {
        return planNoBind;
    }

    public void editDialogListShade(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {


            OperationBinding operationBinding =
                executeOperation("FillRollLines");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rollTable);


        }

    }


    public void editPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {

    }


    public void editPopupFetchListener(PopupFetchEvent popupFetchEvent) {

        OperationBinding operationBinding =
            executeOperation("setRollwhereClause");
        operationBinding.execute();
    }

    /*****Generic Method to Get BindingContainer**/
    public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam =
            getBindingsCont().getOperationBinding(operation);
        return createParam;

    }


    public void setRollTable(RichTable rollTable) {
        this.rollTable = rollTable;
    }

    public RichTable getRollTable() {
        return rollTable;
    }

    public String ReportBundleCard() {
        // Add event code here...
        return null;
    }

    public String AssortBundleStatement() {
        // Add event code here...
        return null;
    }

    public String matrixReport() {
        OperationBinding ob = executeOperation("callMatrix");
        ob.execute();

        Object methodReturnValue = ob.getResult();
        String message = null;
        if (methodReturnValue != null) {
            message = methodReturnValue.toString();
        } else {
            message = "Failed!";
        }
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);

        return null;
    }

    public void setTimeCal(RichInputText timeCal) {
        this.timeCal = timeCal;
    }

    public RichInputText getTimeCal() {
        return timeCal;
    }

    public void valueChangerDate(ValueChangeEvent valueChangeEvent) {

        long TimeCalculation = 0;
        if (getEndTime().getValue() != null &&
            getStartTime().getValue() != null)

        {
            TimeCalculation =
                    printDate(getEndTime().getValue().toString(), getStartTime().getValue().toString());

            TimeCalculation = TimeCalculation / 60000;
            // TimeCalculation = TimeCalculation / 1000;   Seconds
            // TimeCalculation = TimeCalculation / 60;   Minutes

            timeCal.setValue(TimeCalculation);
            AdfFacesContext.getCurrentInstance().addPartialTarget(timeCal);
            System.out.println("timeCal --->" + TimeCalculation);
        } else {
            timeCal.setValue(0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(timeCal);

        }
    }

    public long printDate(String date, String seconddate) {

        //  DateFormat df = new SimpleDateFormat("hh:mm:ss");
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        java.util.Date date1 = new java.util.Date();
        java.util.Date seconddate1 = new java.util.Date();
        //java.util.Date resultdate  = new java.util.Date();
        //String resultdate;
        long resultdate = -1;

        try {
            date1 = df.parse(date);
            seconddate1 = df.parse(seconddate);

            resultdate = date1.getTime() - seconddate1.getTime();


        } catch (Exception e) {
            ;
        }

        return resultdate;
    }

    public void setStartTime(RichInputDate startTime) {
        this.startTime = startTime;
    }

    public RichInputDate getStartTime() {
        return startTime;
    }

    public void setEndTime(RichInputDate endTime) {
        this.endTime = endTime;
    }

    public RichInputDate getEndTime() {
        return endTime;
    }


    public void GoToCompletion(ActionEvent actionEvent) {
        // Add event code here...
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response =
            (HttpServletResponse)fc.getExternalContext().getResponse();
        try {
            response.sendRedirect("/CuttingProduction-ViewController-context-root/faces/SearchPG.jspx");
        } catch (IOException e) {
        }
        fc.responseComplete();

    }

    public void setFromPieceNoBinding(RichInputText fromPieceNoBinding) {
        this.fromPieceNoBinding = fromPieceNoBinding;
    }

    public RichInputText getFromPieceNoBinding() {
        return fromPieceNoBinding;
    }

    public void setToPieceNoBinding(RichInputText toPieceNoBinding) {
        this.toPieceNoBinding = toPieceNoBinding;
    }

    public RichInputText getToPieceNoBinding() {
        return toPieceNoBinding;
    }

    public void setTotalBundleQuantityBinding(RichOutputText totalBundleQuantityBinding) {
        this.totalBundleQuantityBinding = totalBundleQuantityBinding;
    }

    public RichOutputText getTotalBundleQuantityBinding() {
        return totalBundleQuantityBinding;
    }

    public void splitBundleValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        int FromPiece, ToPiece, TotalPieces, MinBundleQuantity = 0;

        try {
            FromPiece =
                    Integer.parseInt(getFromPieceNoBinding().getValue().toString());

        } catch (Exception e) {
            // TODO: Add catch code
            FromPiece = 0;

        }

        System.out.println("FromPiece..." + FromPiece);
        try {
            ToPiece =
                    Integer.parseInt(valueChangeEvent.getNewValue().toString());

        } catch (Exception e) {
            // TODO: Add catch code
            ToPiece = 0;

        }

        System.out.println("ToPiece..." + ToPiece);

        TotalPieces = ToPiece - FromPiece;
        TotalPieces = TotalPieces + 1;

        System.out.println("TotalPieces..." + TotalPieces);

        // Coding to Pick Minimum Quantity

        RowSetIterator it = MnjMfgCutlyrcntrlLView1.createRowSetIterator("tt");
        Row currentRow = MnjMfgCutlyrcntrlLView1.getCurrentRow();

        try {
            MinBundleQuantity =
                    Integer.parseInt(currentRow.getAttribute("MinBunQty").toString());
        } catch (Exception e) {
            ;
        }

        System.out.println("MinBundleQuantity..." + MinBundleQuantity);

        it.closeRowSetIterator();

        // Coding to Pick Minimum Quantity

        //        if( TotalPieces  < MinBundleQuantity ){
        //            FacesContext context = FacesContext.getCurrentInstance();
        //            FacesMessage message = new FacesMessage("Can't Enter Value Less Than Minimun Bundle Quantity");
        //            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            context.addMessage(valueChangeEvent.getComponent().getClientId(context), message);
        //
        //            // Reset inputFile component after upload
        //            ResetUtils.reset(valueChangeEvent.getComponent());
        //        }
        //        else{
        RowSetIterator it1 = BundleDetailVO1.createRowSetIterator("tt");
        Row currentRow1 = BundleDetailVO1.getCurrentRow();

        currentRow1.setAttribute("TotalBundleQuantity", TotalPieces);

        //   totalBundleQuantityBinding.setValue(TotalPieces);

        it1.closeRowSetIterator();

        AdfFacesContext.getCurrentInstance().addPartialTarget(totalBundleQuantityBinding);
        //            };


    }

    public void splitBundleValueChangeListenerFrom(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        int FromPiece, ToPiece, TotalPieces, MinBundleQuantity = 0;

        try {
            // FromPiece = Integer.parseInt(getFromPieceNoBinding().getValue().toString());
            FromPiece =
                    Integer.parseInt(valueChangeEvent.getNewValue().toString());

        } catch (Exception e) {
            // TODO: Add catch code
            FromPiece = 0;

        }

        System.out.println("FromPiece..." + FromPiece);
        try {
            //   ToPiece   = Integer.parseInt(valueChangeEvent.getNewValue().toString());
            ToPiece =
                    Integer.parseInt(getToPieceNoBinding().getValue().toString());

        } catch (Exception e) {
            // TODO: Add catch code
            ToPiece = 0;

        }

        System.out.println("ToPiece..." + ToPiece);

        TotalPieces = ToPiece - FromPiece;
        TotalPieces = TotalPieces + 1;

        System.out.println("TotalPieces..." + TotalPieces);

        // Coding to Pick Minimum Quantity

        RowSetIterator it = MnjMfgCutlyrcntrlLView1.createRowSetIterator("tt");
        Row currentRow = MnjMfgCutlyrcntrlLView1.getCurrentRow();

        try {
            MinBundleQuantity =
                    Integer.parseInt(currentRow.getAttribute("MinBunQty").toString());
        } catch (Exception e) {
            ;
        }

        System.out.println("MinBundleQuantity..." + MinBundleQuantity);

        it.closeRowSetIterator();

        // Coding to Pick Minimum Quantity

        //        if( TotalPieces  < MinBundleQuantity ){
        //            FacesContext context = FacesContext.getCurrentInstance();
        //            FacesMessage message = new FacesMessage("Can't Enter Value Less Than Minimun Bundle Quantity");
        //            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            context.addMessage(valueChangeEvent.getComponent().getClientId(context), message);
        //
        //            // Reset inputFile component after upload
        //            ResetUtils.reset(valueChangeEvent.getComponent());
        //        }
        //        else{
        RowSetIterator it1 = BundleDetailVO1.createRowSetIterator("tt");
        Row currentRow1 = BundleDetailVO1.getCurrentRow();

        currentRow1.setAttribute("TotalBundleQuantity", TotalPieces);

        //   totalBundleQuantityBinding.setValue(TotalPieces);

        it1.closeRowSetIterator();

        AdfFacesContext.getCurrentInstance().addPartialTarget(totalBundleQuantityBinding);
        //            };


    }

    public void SplitBundle(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding operationBinding = executeOperation("SplitBundles");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bundleTable);

    }

    public void setBundleTable(RichTable bundleTable) {
        this.bundleTable = bundleTable;
    }

    public RichTable getBundleTable() {
        return bundleTable;
    }

    public void setCutPlanNumber(RichInputListOfValues cutPlanNumber) {
        this.cutPlanNumber = cutPlanNumber;
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        HttpSession userSession = (HttpSession)ectx.getSession(false);
        userSession.setAttribute("planNos", cutPlanNumber.getValue());
        System.out.println(userSession.getAttribute("planNos"));
    }

    public RichInputListOfValues getCutPlanNumber() {
        return cutPlanNumber;
    }

    public void save(ActionEvent actionEvent) {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContext.findDataControl("AppModuleDataControl"); //
        ApplicationModule am = dc.getApplicationModule();
        ViewObject HeaderVo = am.findViewObject("MnjMfgCutlyrcntrlHView1");

        OperationBinding operationBinding = executeOperation("save");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelformlayout_H);
        //HeaderVo.executeQuery();
        
    }

    public void Calculate_Cons(ActionEvent actionEvent) {
        // Add event code here...
        initCalculate_Cons(String.valueOf(getHeaderId().getValue()));
    }
    
    public void initCalculate_Cons(String HeaderId) {


        OperationBinding operationBinding = executeOperation("Calculate_Cons");

        operationBinding.getParamsMap().put("HeaderId", HeaderId);
        operationBinding.execute();

        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("if errors-->");
        }

        //optional
        Object methodReturnValue = operationBinding.getResult();
        String message = null;
        if (methodReturnValue != null) {
            message = methodReturnValue.toString();
        } else {
            message = "Failed! .";
        }
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);

    }

    public void setHeaderId(RichInputText headerId) {
        this.headerId = headerId;
    }

    public RichInputText getHeaderId() {
        return headerId;
    }

    public void setPanelformlayout_H(RichPanelFormLayout panelformlayout_H) {
        this.panelformlayout_H = panelformlayout_H;
    }

    public RichPanelFormLayout getPanelformlayout_H() {
        return panelformlayout_H;
    }
}
