<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Contato extends Model
{
    protected $table = 'contatos';
    protected $primaryKey = 'id_contato';
    public $timestamps = false;

    protected $fillable = [
        'tp_contato',
        'ds_contato',
        'id_usuario',
    ];

    protected $casts = [
        'dataAlteracao' => 'datetime',
    ];
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'id_usuario', 'id_usuario');
    }
}
