// var token = $("meta[name='_csrf']").attr("content");
// var header = $("meta[name='_csrf_header']").attr("content");
// var total = 0;
$(document).ready(function () {
    search("");
});

// function pagination(currentPage) {
//     // @TODO check pagination
//     console.log("current " + currentPage + ", total " + total);
//     $('#pagination-demo').twbsPagination({
//         totalPages: total,
//         page: currentPage,
//         visiblePages: "1",
//         onPageClick: function (event, page) {
//             console.log("clicked page " + page);
//             var query = $("#input-search-docs").val();
//             // if (search) {
//                 search(query, page);
//             // }
//         }
//     });
// }

function search(query) {
    // console.log("page " + page + ", total " + total);
    $.ajax({
        type: "GET",
        url: "/api/document/search",
        data: {ownerId: company, query: query},
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var documentsHtml = "";
            if (data.total > 0) {
                // total = data.total;
                for (var i = 0; i < data.documents.length; i++) {
                    documentsHtml += '<ul class="list-group">' +
                        '<li class="list-group-item clearfix">' +
                        '<a class="btn btn-default pull-right" href="/api/document/download/' + data.documents[i].id + '" title="Download">' +
                        '<span class="icon_folder_download"></span> Download</a>' +
                        '<a class="btn btn-default pull-right" href="/api/document/' + company + "/" + data.documents[i].id + '" target="_blank" title="View file">' +
                        '<span class="icon_folder-open"></span> View</a>' +
                        '<h4 class="list-group-item-heading">' + data.documents[i].fileName + '</h4>';
                    for (var j = 0; j < data.documents[i].descriptors.length; j++) {
                        documentsHtml += '<p class="list-group-item-text">' +
                            '<strong>' + data.documents[i].descriptors[j].descriptorKey + ': </strong>' +
                            data.documents[i].descriptors[j].descriptorValue + '</p>';
                    }
                    documentsHtml += ' </li> </ul>';
                }
            }
            // pagination(page)
            $("#documents").html(documentsHtml);
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}