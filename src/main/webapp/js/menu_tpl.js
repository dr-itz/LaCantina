/* menu level scope settings structure */
var MENU_POS = [
{
    // item sizes
    'height': 22,
    'width': 140,
    // menu block offset from the origin:
    //	for root level origin is upper left corner of the page
    //	for other levels origin is upper left corner of parent item
    'block_top': 32,
    'block_left': 2,
    // offsets between items of the same level
    'top': 0,
    'left': 139,
    // time in milliseconds before menu is hidden after cursor has gone out
    // of any items
    //'hide_delay': 200,
    //'expd_delay': 200,
    'css' : {
        'outer': ['m0l0oout', 'm0l0oover'],
        'inner': ['m0l0iout', 'm0l0iover']
    }
},
{
    'height': 22,
    'width': 220,
    'block_top': 22,
    'block_left': 0,
    'top': 23,
    'left': 0,
    'css' : {
		'outer' : ['m0l1oout', 'm0l1oover'],
		'inner' : ['m0l0iout', 'm0l0iover']
	}
},
{
    'block_top': 4,
    'block_left': 100

}
]

