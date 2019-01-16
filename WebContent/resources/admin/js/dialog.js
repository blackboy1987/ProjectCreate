

$().ready( function() {
	var $dialogContract = $(".dialogContract");
	var $dialogStaff = $(".dialogStaff");
	
	/**
	 * 合同
	 */
	$dialogContract.live("click",function(){
		var $this = $(this);
		layer.open({
			title:$this.data("title"),
		  type: 2,
		  area: ['700px', '450px'],
		  fixed: false,
		  closeBtn:1,
		  content: $this.data("url")+"?i&contractId="+$("#contractId").val()
		});
	});
	
	/**
	 * 员工
	 */
	$dialogStaff.live("click",function(){
		var $this = $(this);
		layer.open({
			title:$this.data("title"),
		  type: 2,
		  area: ['700px', '450px'],
		  fixed: false,
		  closeBtn:1,
		  content: $this.data("url")+"?i&staffId="+$("#staffId").val()
		});
	});
});