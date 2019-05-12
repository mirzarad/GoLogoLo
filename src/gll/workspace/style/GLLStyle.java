package gll.workspace.style;

/**
 * This class lists all CSS style types for this application. These
 * are used by JavaFX to apply style properties to controls like
 * buttons, labels, and panes.

 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class GLLStyle {
    public static final String EMPTY_TEXT = "";
    public static final int BUTTON_TAG_WIDTH = 75;

    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS M3Workspace'S COMPONENTS TO A STYLE SHEET THAT IT USES
    // NOTE THAT FOUR CLASS STYLES ALREADY EXIST:
    // top_toolbar, toolbar, toolbar_text_button, toolbar_icon_button
    
    public static final String CLASS_GLL_PANE          = "gll_pane";
    public static final String CLASS_GLL_BOX           = "gll_box";            
    public static final String CLASS_GLL_BIG_HEADER    = "gll_big_header";
    public static final String CLASS_GLL_SMALL_HEADER  = "gll_small_header";
    public static final String CLASS_GLL_PROMPT        = "gll_prompt";
    public static final String CLASS_GLL_TEXT_FIELD    = "gll_text_field";
    public static final String CLASS_GLL_BUTTON        = "gll_button";
    public static final String CLASS_GLL_TABLE         = "gll_table";
    public static final String CLASS_GLL_COLUMN        = "gll_column";
    public static final String CLASS_GLL_COMBOBOX      = "gll_combobox";
    public static final String CLASS_GLL_TOOLBAR      = "gll_toolbar";
    public static final String CLASS_GLL_WORKSPACE    = "gll_workspace";
    public static final String CLASS_GLL_LABEL    = "gll_label";
    
    // STYLE CLASSES FOR THE ADD/EDIT ITEM DIALOG
    public static final String CLASS_GLL_DIALOG_GRID           = "gll_dialog_grid";
    public static final String CLASS_GLL_DIALOG_HEADER         = "gll_dialog_header";
    public static final String CLASS_GLL_DIALOG_PROMPT         = "gll_dialog_prompt";
    public static final String CLASS_GLL_DIALOG_TEXT_FIELD     = "gll_dialog_text_field";
    public static final String CLASS_GLL_DIALOG_DATE_PICKER    = "gll_dialog_date_picker";
    public static final String CLASS_GLL_DIALOG_CHECK_BOX      = "gll_dialog_check_box";
    public static final String CLASS_GLL_DIALOG_BUTTON         = "gll_dialog_button";
    public static final String CLASS_GLL_DIALOG_PANE           = "gll_dialog_pane";
}