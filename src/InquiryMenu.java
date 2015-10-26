
public enum InquiryMenu {
        CreateNew(0), Display(1), Load(2), Save(3);
        private int value;

        private InquiryMenu(int value) 
        {
                this.value = value;
        }
        public int GetValue()
        {
        	return value;
        } 
};   
