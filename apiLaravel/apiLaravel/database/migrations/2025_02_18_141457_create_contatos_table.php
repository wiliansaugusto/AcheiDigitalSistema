<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('contatos', function (Blueprint $table) {
            $table->bigIncrements('id_contato');
            $table->string('tp_contato', 20);
            $table->string('ds_contato', 100);
            $table->timestamp('dataAlteracao')->useCurrent();
            $table->unsignedBigInteger('id_usuario'); // Chave estrangeira
            // Define a foreign key (equivalente ao @ManyToOne)
            $table->foreign('id_usuario')
                ->references('id_usuario')
                ->on('usuarios')
                ->onDelete('cascade'); // Ajuste conforme a necessidade

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('contatos');
    }
};
