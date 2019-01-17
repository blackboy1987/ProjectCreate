

$().ready( function() {
	function bingProductAutocomplete(event){
		$(event).autocomplete(shopxx.base+"/admin/product/search", {
			max: 20,
			width: 300,
			scrollHeight: 300, 
			matchContains: true,
			parse: function(data) {
				return $.map(data, function(item) {
					return {
						data: item,
						value: item.name
					}
				});
			},
			formatItem: function(item) {
				return '<span title="' + escapeHtml(item.name) + '">' + escapeHtml(abbreviate(item.name, 50, "...")) + '<\/span>';
			}
		}).result(function(event, item) {
			console.log(this);
			$(this).val(item.name);
			$(this).next().val(item.id);
		});
	}
	
	
	$(".selectProduct").live("focus",function(){
		bingProductAutocomplete(this);
	})
});